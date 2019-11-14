package com.androidschool.mvp_homework.presentation.presenter;

import com.androidschool.mvp_homework.data.model.PackageModel;
import com.androidschool.mvp_homework.data.repository.PackageModelRepository;
import com.androidschool.mvp_homework.presentation.view.IMainView;
import com.androidschool.mvp_homework.presentation.view.IPackageRowView;
import com.androidschool.mvp_homework.presentation.view.enums.SortMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PackagesPresenterTest {
    @Mock
    private IMainView mPackageInstalledView;

    @Mock
    private IPackageRowView mPackageRowView;

    @Mock
    private PackageModelRepository mPackageInstalledRepository;

    private PackagePresenter mMainPresenter;

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    public void setUp() {
        mMainPresenter = new PackagePresenter(mPackageInstalledView, mPackageInstalledRepository);
        mMainPresenter.setPackageModels(createTestData());
    }

    /**
     * Тестирование синхронного получения данных в презентере.
     */
    @Test
    public void testLoadDataSync() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.getData()).thenReturn(createTestData());

        //Вызов тестируемого метода
        mMainPresenter.loadDataSync();

        //Проверка, что презентер действительно вызывает методы представления
        verify(mPackageInstalledView).showProgress();
        verify(mPackageInstalledView).showData();
        verify(mPackageInstalledView).hideProgress();
    }

    /**
     * Тестирование синхронного метода получения данных в презентере.
     * <p> В данном тесте дополнительно проверяется порядок вызова методов. Попробуйте поменять очередность или добавить какой-либо вызов
     * метода {@link IMainView} в {@link PackagePresenter} и увидите, что данный тест не пройдет.
     */
    @Test
    public void testLoadDataSync_withOrder() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.getData()).thenReturn(createTestData());

        //Вызов тестируемого метода
        mMainPresenter.loadDataSync();

        InOrder inOrder = Mockito.inOrder(mPackageInstalledView);

        //Проверка, что презентер действительно вызывает методы представления, причем в порядке вызова этих методов. Можно сравнить с предыдущим тестом.
        inOrder.verify(mPackageInstalledView).showProgress();
        inOrder.verify(mPackageInstalledView).hideProgress();
        inOrder.verify(mPackageInstalledView).showData();

        //Проверка, что никакой метод не будет вызван у mPackageInstalledView.
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Тестирование асинхронного метода получения данных в презентере.
     */
    @Test
    public void testLoadDataAsync() {
        //Здесь происходит магия. Нам нужно выдернуть аргмуент, переданный в mPackageInstalledRepository в качетсве слушателя и немедленно вернуть
        //какой-то результат. Ведь нам неважно, каким образом отработает mPackageInstalledRepository#loadDataAsync(), важно, что этот метод должен вернуть
        //в колбеке.
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                //получаем слушателя из метода loadDataAsync().
                PackageModelRepository.OnLoadingFinishListener onLoadingFinishListener =
                        (PackageModelRepository.OnLoadingFinishListener) invocation.getArguments()[0];

                //кидаем в него ответы
                onLoadingFinishListener.onFinish(createTestData());

                return null;
            }
        }).when(mPackageInstalledRepository).loadDataAsync(Mockito.any(PackageModelRepository.OnLoadingFinishListener.class));

        mMainPresenter.loadDataAsync();

        //Далее просто проверяем, что все будет вызвано в нужном порядке.
        InOrder inOrder = Mockito.inOrder(mPackageInstalledView);
        inOrder.verify(mPackageInstalledView).showProgress();
        inOrder.verify(mPackageInstalledView).hideProgress();
        inOrder.verify(mPackageInstalledView).showData();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testRefreshShowSystem() {
        mMainPresenter.refreshShowSystem(true);
        verify(mPackageInstalledView).showData();

        verifyNoMoreInteractions(mPackageInstalledView);
    }

    @Test
    public void testRefreshSort(){
        mMainPresenter.refreshSort(SortMode.SORT_BY_APP_NAME);
        verify(mPackageInstalledView).showData();

        verifyNoMoreInteractions(mPackageInstalledView);
    }

    /**
     * Тестирование {@link PackagePresenter#detachView()}.
     *
     * <p> после детача, все методы не будут ничего прокидывать в {@link IMainView}.
     */
    @Test
    public void testDetachView() {
        mMainPresenter.detachView();

        mMainPresenter.loadDataAsync();
        mMainPresenter.loadDataSync();

        verifyNoMoreInteractions(mPackageInstalledView);
    }

    private List<PackageModel> createTestData() {
        return new ArrayList<PackageModel>() {
            {
                add(new PackageModel("Sberbank",
                        "ru.sberbankmobile", null, true, "system"));
                add(new PackageModel("Test", "de.test.besteapp",
                        null, false, ""));
            }
        };
    }

    @Test
    public void testOnBindPackageRowViewAtPosition() {
        PackageModel model = createTestData().get(0);
        mMainPresenter.onBindPackageRowViewAtPosition(0, mPackageRowView);
        verify(mPackageRowView).setAppName(model.getAppName());
        verify(mPackageRowView).setAppPackageName(model.getAppPackageName());
        verify(mPackageRowView).setAppIcon(model.getAppIcon());
        verify(mPackageRowView).setSystemText(model.getSystemLabel());

        verifyNoMoreInteractions(mPackageRowView);
    }

}
