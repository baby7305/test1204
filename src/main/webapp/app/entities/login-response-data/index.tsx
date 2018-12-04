import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LoginResponseData from './login-response-data';
import LoginResponseDataDetail from './login-response-data-detail';
import LoginResponseDataUpdate from './login-response-data-update';
import LoginResponseDataDeleteDialog from './login-response-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LoginResponseDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LoginResponseDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LoginResponseDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={LoginResponseData} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LoginResponseDataDeleteDialog} />
  </>
);

export default Routes;
