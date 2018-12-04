import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILoginResponseData, defaultValue } from 'app/shared/model/login-response-data.model';

export const ACTION_TYPES = {
  FETCH_LOGINRESPONSEDATA_LIST: 'loginResponseData/FETCH_LOGINRESPONSEDATA_LIST',
  FETCH_LOGINRESPONSEDATA: 'loginResponseData/FETCH_LOGINRESPONSEDATA',
  CREATE_LOGINRESPONSEDATA: 'loginResponseData/CREATE_LOGINRESPONSEDATA',
  UPDATE_LOGINRESPONSEDATA: 'loginResponseData/UPDATE_LOGINRESPONSEDATA',
  DELETE_LOGINRESPONSEDATA: 'loginResponseData/DELETE_LOGINRESPONSEDATA',
  RESET: 'loginResponseData/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILoginResponseData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LoginResponseDataState = Readonly<typeof initialState>;

// Reducer

export default (state: LoginResponseDataState = initialState, action): LoginResponseDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LOGINRESPONSEDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOGINRESPONSEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LOGINRESPONSEDATA):
    case REQUEST(ACTION_TYPES.UPDATE_LOGINRESPONSEDATA):
    case REQUEST(ACTION_TYPES.DELETE_LOGINRESPONSEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LOGINRESPONSEDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LOGINRESPONSEDATA):
    case FAILURE(ACTION_TYPES.CREATE_LOGINRESPONSEDATA):
    case FAILURE(ACTION_TYPES.UPDATE_LOGINRESPONSEDATA):
    case FAILURE(ACTION_TYPES.DELETE_LOGINRESPONSEDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGINRESPONSEDATA_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGINRESPONSEDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOGINRESPONSEDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_LOGINRESPONSEDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOGINRESPONSEDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/login-response-data';

// Actions

export const getEntities: ICrudGetAllAction<ILoginResponseData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LOGINRESPONSEDATA_LIST,
    payload: axios.get<ILoginResponseData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILoginResponseData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOGINRESPONSEDATA,
    payload: axios.get<ILoginResponseData>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILoginResponseData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOGINRESPONSEDATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILoginResponseData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOGINRESPONSEDATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILoginResponseData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOGINRESPONSEDATA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
