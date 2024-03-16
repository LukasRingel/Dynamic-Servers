import React from "react";
import {ConfigProvider, theme} from "antd";
import ColorSwitchFloatButton from "../color/ColorSwitchFloatButton";
import {ColorMode, darkMode, lightMode} from "../color/ColorMode";
import {storeColorMode} from "../color/ColorModeStorage";

type ItemListProps = {
  children?: React.ReactNode;
  colorMode: ColorMode;
  updateColorMode: (colorMode: ColorMode) => void;
};

const ApplicationContainer: React.FC<ItemListProps> = ({children, colorMode, updateColorMode}) => {
  const {defaultAlgorithm, darkAlgorithm} = theme;

  document.body.style.backgroundColor = colorMode.background;
  document.body.style.color = colorMode.text;

  function switchColor() {
    return () => {
      updateColorMode(colorMode.name === 'light' ? darkMode : lightMode);
      storeColorMode(colorMode.name === 'light' ? darkMode : lightMode)
    };
  }

  return <ConfigProvider
    theme={{
      token: {
        colorPrimary: colorMode.primary,
        colorPrimaryText: colorMode.text,
        colorBgContainer: colorMode.background,
        colorText: colorMode.text,
      },
      components: {
        Card: {
          colorBgContainer: colorMode.headerBackground,
        },
        App: {
          colorBgContainer: colorMode.background,
        },
        Input: {
          colorText: colorMode.text,
          colorTextBase: colorMode.text,
          colorTextLabel: colorMode.text,
          colorTextPlaceholder: colorMode.text,
        }
      },
      algorithm: colorMode.name === 'light' ? defaultAlgorithm : darkAlgorithm
    }}>
    <ColorSwitchFloatButton switchColor={switchColor()} current={colorMode}/>
    {children}
  </ConfigProvider>
}

export default ApplicationContainer;