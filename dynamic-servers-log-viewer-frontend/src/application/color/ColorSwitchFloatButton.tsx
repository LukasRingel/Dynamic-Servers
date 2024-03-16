import React from "react";
import {FloatButton} from "antd";
import {ColorMode} from "./ColorMode";

type ItemListProps = {
  current: ColorMode;
  switchColor: () => void;
};

const ColorSwitchFloatButton: React.FC<ItemListProps> = ({switchColor, current}) => {
  return <FloatButton onClick={switchColor} icon={current.icon}></FloatButton>
}

export default ColorSwitchFloatButton;