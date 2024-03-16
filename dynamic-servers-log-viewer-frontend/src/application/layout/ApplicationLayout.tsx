import React, {useState} from "react";
import {Route, Routes} from "react-router";
import {Layout} from "antd";
import NavigationSidebarComponent from "../pages/navigation/NavigationSidebarComponent";
import {routes} from "../pages/navigation/NavigationUtils";
import {ColorMode} from "../color/ColorMode";
import ApplicationContainer from "./ApplicationContainer";
import {loadColorMode} from "../color/ColorModeStorage";
import ApplicationHeader from "./ApplicationHeader";

type ItemListProps = {};
const {Header, Content, Sider} = Layout;

const ApplicationLayout: React.FC<ItemListProps> = () => {
  const [collapsed, setCollapsed] = useState(true);
  const [colorMode, setColorMode] = useState<ColorMode>(loadColorMode);

  const colorModeBackgroundColor = {
    backgroundColor: colorMode.background,
    color: colorMode.text,
  }

  const headerColorAndAlign = {
    backgroundColor: colorMode.headerBackground,
    display: 'flex',
    alignItems: 'center',
  }

  return (
    <Layout style={{minHeight: '100vh'}}>
      <Sider collapsible breakpoint={"lg"} collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
        <NavigationSidebarComponent/>
      </Sider>
      <Layout>
        <Header style={headerColorAndAlign}>
          <ApplicationHeader colorMode={colorMode}/>
        </Header>
        <Content style={colorModeBackgroundColor}>
          <ApplicationContainer colorMode={colorMode} updateColorMode={setColorMode}>
            <Routes>
              {routes.map(route => {
                return <Route key={route.path} path={route.path} Component={(route.page)}/>
              })}
            </Routes>
          </ApplicationContainer>
        </Content>
      </Layout>
    </Layout>
  )
}

export default ApplicationLayout;