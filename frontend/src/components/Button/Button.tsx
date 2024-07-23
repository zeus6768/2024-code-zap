import React, { PropsWithChildren } from 'react';
import * as S from './style';

export interface Props {
  onClick?: (e?: React.MouseEvent<HTMLButtonElement>) => void;
  type?: 'button' | 'submit' | 'reset';
  variant: 'default' | 'outlined' | 'text';
  size: 'small' | 'medium';
  width?: string;
  disabled?: boolean;
}

const Button = ({ children, onClick, type, variant, size, width, disabled }: PropsWithChildren<Props>) => (
  <S.Button type={type || 'button'} variant={variant} size={size} width={width} disabled={disabled} onClick={onClick}>
    {children}
  </S.Button>
);

export default Button;
