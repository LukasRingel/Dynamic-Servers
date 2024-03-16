import React from "react";
import {Spin} from "antd";
import {LoadingOutlined} from "@ant-design/icons";
import Centered from "./Centered";

type ItemListProps = {
  size?: number;
  color?: string;
};

const LoadingSpinner: React.FC<ItemListProps> = (props) => {
  let spinnerColor = props.color ? props.color : '#1890ff';
  return <Centered style={{marginTop: 30}}>
    <Spin spinning={true} indicator={
      <LoadingOutlined color={spinnerColor} style={{fontSize: props.size}}/>
    }/>
  </Centered>
}

export default LoadingSpinner;