import React from "react";
import {Image, Menu} from "antd";
import {Link, useLocation} from "react-router-dom";
import {DesktopOutlined, SearchOutlined} from "@ant-design/icons";
import Logo from './logo.png'

type ItemListProps = {};

function createNavigationEntry(label: React.ReactNode, key: React.Key, to: string, icon?: React.ReactNode, children?: MenuItem[]): MenuItem {
  return {key, icon, children, label, to};
}

type MenuItem = {
  key: React.Key,
  icon?: React.ReactNode,
  children?: MenuItem[],
  label: React.ReactNode,
  to: string,
}

const NavigationSidebarComponent: React.FC<ItemListProps> = () => {
  const location = useLocation();

  const loadNavbarItems = (): MenuItem[] => {
    return [
      createNavigationEntry("Dashboard", '1', "/", <DesktopOutlined/>),
      createNavigationEntry("Search", '2.1', "/search", <SearchOutlined/>)
    ]
  }

  return (
    <div>
      <Image style={{marginTop: 25, marginBottom: 25}} src={Logo} preview={false}/>
      <Menu
        theme={"dark"}
        defaultSelectedKeys={["1"]}
        selectedKeys={[location.pathname]}
        mode="inline"
      >
        {loadNavbarItems().map(item => {
          if (item.children !== undefined) {
            return <Menu.SubMenu key={item.key} icon={item.icon} title={item.label}>
              {item.children.map(child => {
                return <Menu.Item key={child.key}>
                  <Link to={child.to}>{child.label}</Link>
                </Menu.Item>
              })}
            </Menu.SubMenu>
          }
          return <Menu.Item key={item.key} icon={item.icon}>
            <Link to={item.to}>{item.label}</Link>
          </Menu.Item>
        })}
      </Menu>
    </div>
  )
}

export default NavigationSidebarComponent;