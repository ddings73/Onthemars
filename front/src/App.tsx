import styled from 'styled-components';
import GlobalFont from './styles/GlobalFont';
import Router from './routes';
import Home from './Containers';

function App() {
  return (
    <Container>
      <GlobalFont />
      <Router />
    </Container>
  );
}
export default App;

const Container = styled.div`
  width: 100vw;
  position: fixed;
  height: 100vh;
  font-family:"BMJUA";
  @media ${(props) => props.theme.tablet} {
    background-image: none;
    background-color: ${(props) => props.theme.colors.lightPink};
  }
`;
