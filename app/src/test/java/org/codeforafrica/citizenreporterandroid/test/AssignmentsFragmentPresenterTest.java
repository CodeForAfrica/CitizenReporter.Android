package org.codeforafrica.citizenreporterandroid.test;

import java.util.Arrays;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentFragmentContract;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentsFragmentPresenter;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static java.util.Collections.*;
import static org.mockito.Mockito.*;

/**
 * Created by Ahereza on 8/31/17.
 */
public class AssignmentsFragmentPresenterTest {
  @Mock AssignmentFragmentContract.View view;
  AssignmentsFragmentPresenter presenter;
  @Mock
  DataManager manager;

  List<Assignment> MANY_ASSIGNMENTS = Arrays.asList(new Assignment(), new Assignment());

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Test public void shouldPassAssignments() {
    when(manager.loadAssignments()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(view, manager);
    presenter.getAssignments();

    verify(view).displayAssignments(MANY_ASSIGNMENTS);
  }

  @Test public void shouldHandleNoAssignments() {

    when(manager.loadAssignments()).thenReturn(EMPTY_LIST);

    presenter = new AssignmentsFragmentPresenter(view, manager);
    presenter.getAssignments();

    verify(view).displayNoAssignments();
  }

  @Test public void shouldShowProgressBarWhenLoadingAssignments(){
    when(manager.loadAssignments()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(view, manager);
    presenter.getAssignments();

    verify(view).showLoading();

  }

  @Test public void shouldHideProgressBarAfterLoadingAssignments() {
    when(manager.loadAssignments()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(view, manager);
    presenter.getAssignments();

    verify(view).hideLoading();
  }

  @Test public void shouldFetchAssignmentsFromAPIAndSaveThemToDbthenDisplayThemOnRefresh(){
    when(manager.fetchAssignmentsAPI()).thenReturn(MANY_ASSIGNMENTS);
    when(manager.loadAssignments()).thenReturn(MANY_ASSIGNMENTS);

    presenter = new AssignmentsFragmentPresenter(view, manager);

    presenter.pullToRefreshAssignments();

    verify(view).showLoading();
    verify(manager).clearAssignmentsTable();
    verify(manager).fetchAssignmentsAPI();
    verify(manager).saveAssignmentsIntoDb(MANY_ASSIGNMENTS);
    verify(manager).loadAssignments();
    verify(view).displayAssignments(MANY_ASSIGNMENTS);
    verify(view).hideLoading();
  }

  @Test public void shouldOpenAssignmentDetail() {
    presenter = new AssignmentsFragmentPresenter(view, manager);
    Assignment assignment = new Assignment();

    presenter.goToAssignmentDetail(assignment);

    verify(view).showAssignmentDetails();
  }
}