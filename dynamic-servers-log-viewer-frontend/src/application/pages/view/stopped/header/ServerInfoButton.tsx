import React from "react";
import {Button, Tooltip} from "antd";
import {InfoOutlined} from "@ant-design/icons";

type ItemListProps = {
  toggle: () => void
};

const buttonDistance = {
  marginRight: "5px"
}

const ServerInfoButton: React.FC<ItemListProps> = ({toggle}) => {
  return <Tooltip title={"Server Info"}>
    <Button style={buttonDistance} type={"primary"} onClick={toggle}>
      <InfoOutlined/>
    </Button>
  </Tooltip>
}

export default ServerInfoButton;