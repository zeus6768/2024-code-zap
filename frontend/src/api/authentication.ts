import { LoginRequest, SignupRequest } from '@/types/authentication';
import { customFetch } from './customFetch';

const API_URL = process.env.REACT_APP_API_URL;

export const SIGNUP_API_URL = `${API_URL}/signup`;
export const LOGIN_API_URL = `${API_URL}/login`;
export const CHECK_USERNAME_API_URL = `${API_URL}/check-username`;
export const CHECK_EMAIL_API_URL = `${API_URL}/check-email`;

export const postSignup = async (signupInfo: SignupRequest) =>
  await customFetch({
    method: 'POST',
    url: `${SIGNUP_API_URL}`,
    body: JSON.stringify(signupInfo),
  });

export const postLogin = async (loginInfo: LoginRequest) => {
  const response = await customFetch({
    method: 'POST',
    url: `${LOGIN_API_URL}`,
    body: JSON.stringify(loginInfo),
  });

  localStorage.setItem('token', response.headers.get('Authorization'));

  return response;
};

export const checkUsername = async (username: string) => {
  const params = new URLSearchParams({ username });
  const url = `${CHECK_USERNAME_API_URL}?${params}`;

  return await customFetch({
    url,
  });
};

export const checkEmail = async (email: string) => {
  const params = new URLSearchParams({ email });
  const url = `${CHECK_EMAIL_API_URL}?${params}`;

  const response = await customFetch({ url });

  if (response.status === 409) {
    throw new Error('중복된 이메일입니다.');
  }

  if (!response.ok) {
    throw new Error('서버 에러가 발생했습니다.');
  }

  return {};
};
