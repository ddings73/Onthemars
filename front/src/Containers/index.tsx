import { LayoutFlex } from "../component/layOut/LayoutFlex";
import styled from "styled-components";

function Home() {
  return (
    <LayoutFlex justify={"center"} align={"center"} width={"100%"} height={"100%"}>
      <TestDiv>
        홈
      </TestDiv>
    </LayoutFlex >
  );
}
export default Home;

const TestDiv = styled.div`
    color: ${(props) => props.theme.colors.yellow};

    @media ${(props) => props.theme.bigmobile} {
    color: ${(props) => props.theme.colors.green};
  }
`