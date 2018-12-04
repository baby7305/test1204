import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './login-response-data.reducer';
import { ILoginResponseData } from 'app/shared/model/login-response-data.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILoginResponseDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LoginResponseDataDetail extends React.Component<ILoginResponseDataDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { loginResponseDataEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="test1204App.loginResponseData.detail.title">LoginResponseData</Translate> [
            <b>{loginResponseDataEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="success">
                <Translate contentKey="test1204App.loginResponseData.success">Success</Translate>
              </span>
            </dt>
            <dd>{loginResponseDataEntity.success ? 'true' : 'false'}</dd>
            <dt>
              <span id="message">
                <Translate contentKey="test1204App.loginResponseData.message">Message</Translate>
              </span>
            </dt>
            <dd>{loginResponseDataEntity.message}</dd>
          </dl>
          <Button tag={Link} to="/entity/login-response-data" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/login-response-data/${loginResponseDataEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ loginResponseData }: IRootState) => ({
  loginResponseDataEntity: loginResponseData.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoginResponseDataDetail);
