import React from "react";
import {Button, Tooltip} from "antd";
import {CopyOutlined} from "@ant-design/icons";

type ItemListProps = {};

const CopyLinkButton: React.FC<ItemListProps> = (props) => {
  return <Tooltip title={"Copy Link"}>
    <Button
      type={"primary"}
      onClick={() => {
        navigator.clipboard.writeText(window.location.href).then()
      }}>
      <CopyOutlined/>
    </Button>
  </Tooltip>
}

export default CopyLinkButton;