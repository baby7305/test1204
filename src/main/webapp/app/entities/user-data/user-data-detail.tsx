import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-data.reducer';
import { IUserData } from 'app/shared/model/user-data.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserDataDetail extends React.Component<IUserDataDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userDataEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="test1204App.userData.detail.title">UserData</Translate> [<b>{userDataEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="username">
                <Translate contentKey="test1204App.userData.username">Username</Translate>
              </span>
            </dt>
            <dd>{userDataEntity.username}</dd>
            <dt>
              <span id="password">
                <Translate contentKey="test1204App.userData.password">Password</Translate>
              </span>
            </dt>
            <dd>{userDataEntity.password}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-data" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-data/${userDataEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ userData }: IRootState) => ({
  userDataEntity: userData.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserDataDetail);
