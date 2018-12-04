export interface ILoginResponseData {
  id?: number;
  success?: boolean;
  message?: string;
}

export const defaultValue: Readonly<ILoginResponseData> = {
  success: false
};
