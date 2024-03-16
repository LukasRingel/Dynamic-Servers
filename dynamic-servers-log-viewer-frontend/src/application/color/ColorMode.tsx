import React from "react";
import {MoonOutlined, SunOutlined} from "@ant-design/icons";

export interface ColorMode {
  name: string;
  icon: React.ReactNode;
  background: string;
  headerBackground: string;
  borderColor: string;
  text: string;
  primary: string;
}

export const darkMode: ColorMode = {
  name: 'dark',
  icon: <SunOutlined/>,
  background: '#282C34',
  headerBackground: '#202329',
  borderColor: '#1B1D22',
  text: '#ffffff',
  primary: '#1677ff'
};

export const lightMode: ColorMode = {
  name: 'light',
  icon: <MoonOutlined/>,
  background: '#ffffff',
  headerBackground: '#E2E2E2',
  borderColor: '#CACACA',
  text: '#282C34',
  primary: '#1677ff'
};