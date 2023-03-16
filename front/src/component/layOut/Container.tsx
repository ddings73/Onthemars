// @flow
import styled from 'styled-components';
import { ReactNode } from 'react';

interface Props {
  children: ReactNode;
  width?: string;
  height?: string;
}

export function Container(props: Props) {
  return (
    <LayoutInfo width={props.width ?? '100%'} height={props.height ?? '100vh'}>
      {props.children}
    </LayoutInfo>
  );
}

const LayoutInfo = styled.div<Props>`
  display: flex;
  border: 1px solid red;
  width: ${(props) => props.width};
  height: ${(props) => props.height};
`;
