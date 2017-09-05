package org.codeforafrica.citizenreporterandroid.test;

import android.test.mock.MockContext;
import java.util.Arrays;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.ui.assignments.AssignmentFragmentContract;
import org.codeforafrica.citizenreporterandroid.ui.assignments.AssignmentsFragmentPresenter;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ahereza on 8/31/17.
 */
public class AssignmentsFragmentPresenterTest {
  @Mock AssignmentFragmentContract.View view;
  AssignmentsFragmentPresenter presenter;
  @Mock DataManager manager;
  MockContext mockContext;

  List<Assignment> MANY_ASSIGNMENTS = Arrays.asList(new Assignment(), new Assignment());

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Test public void shouldPassAssignments() {
    when(manager.loadAssignmentsFromDb()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(manager);
    presenter.setView(view);

    presenter.getAndDisplayAssignments();

    verify(view).displayAssignments(MANY_ASSIGNMENTS);
  }

  @Test public void shouldHandleNoAssignments() {

    when(manager.loadAssignmentsFromDb()).thenReturn(EMPTY_LIST);

    presenter = new AssignmentsFragmentPresenter(manager);
    presenter.setView(view);
    presenter.getAndDisplayAssignments();

    verify(view).displayNoAssignments();
  }

  @Test public void shouldShowProgressBarWhenLoadingAssignments(){
    when(manager.loadAssignmentsFromDb()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(manager);
    presenter.setView(view);
    presenter.getAndDisplayAssignments();

    verify(view).showLoading();

  }

  @Test public void shouldHideProgressBarAfterLoadingAssignments() {
    when(manager.loadAssignmentsFromDb()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(manager);
    presenter.setView(view);
    presenter.getAndDisplayAssignments();

    verify(view).hideLoading();
  }

  @Test public void shouldFetchAssignmentsFromAPIAndSaveThemToDbthenDisplayThemOnRefresh(){
    when(manager.loadAssignmentsFromDb()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(manager);
    presenter.setView(view);

    presenter.pullToRefreshAssignments();

    verify(view).showLoading();
    verify(manager).getAssignments();
    verify(manager).loadAssignmentsFromDb();
    verify(view).displayAssignments(MANY_ASSIGNMENTS);
    verify(view).hideLoading();
  }

}