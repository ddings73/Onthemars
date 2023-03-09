import styled from "styled-components";
import DescriptionContainer from "./DescriptionContainer";
import GameContainer from "./GameContainer";

function Main() {
  return (
    <div>
      <GameContainer />
      <DescriptionContainer />
      <DescriptionContainer />
    </div>
  );
}
export default Main;

const TestDiv = styled.div`
    color: ${(props) => props.theme.colors.yellow};

    @media ${(props) => props.theme.bigmobile} {
    color: ${(props) => props.theme.colors.green};
  }
`