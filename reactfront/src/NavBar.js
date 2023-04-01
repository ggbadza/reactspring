import { Container,Nav,Navbar } from 'react-bootstrap';
import { getCookie,removeCookie } from './User/cookies';

function NavBar(){
  const onClickLogout = () => {
    removeCookie("Authorization");
  }
    return (
        <Navbar bg="light" variant="light">
        <Container>
          <Navbar.Brand href="/">TANKMILU</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="/Item">Item</Nav.Link>
            <Nav.Link href="#/Post">Post</Nav.Link>
          </Nav>
          <Nav>
          {getCookie("Authorization") ? (<Nav>
            <Nav.Link href="/setting">설정</Nav.Link>
            <Nav.Link href="/" onClick={onClickLogout}>로그아웃</Nav.Link>
            </Nav>
          ) : (<Nav>
            <Nav.Link href="/register">회원가입</Nav.Link>
            <Nav.Link href="/login">로그인</Nav.Link>
            </Nav>
          )}
          </Nav>
        </Container>
      </Navbar>
    );
}

export default NavBar;