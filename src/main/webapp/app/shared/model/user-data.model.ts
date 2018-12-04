export interface IUserData {
  id?: number;
  username?: string;
  password?: string;
}

export const defaultValue: Readonly<IUserData> = {};
