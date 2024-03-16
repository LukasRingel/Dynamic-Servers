import React from "react";
import {Flex} from "antd";
import LogStatusCircle from "../../header/LogStatusCircle";
import {loadColorMode} from "../../../../color/ColorModeStorage";
import CopyLinkButton from "../../header/CopyLinkButton";

type ItemListProps = {
  serverId: string
  executor: string;
  startedAt: Date
};

const LiveViewHeader: React.FC<ItemListProps> = (props) => {
  const colorMode = loadColorMode()

  const headerStyles = {
    backgroundColor: colorMode.headerBackground,
    padding: "5px",
    paddingLeft: "10px",
    height: "60px",
  }

  return <Flex vertical={false} justify={"space-between"} align={"center"} style={headerStyles}>
    <Flex align={"center"}>
      <LogStatusCircle running={true}/>
      {props.serverId} on {props.executor} started at {props.startedAt.toLocaleString()}
    </Flex>
    <Flex align={"center"}>
      <CopyLinkButton/>
    </Flex>
  </Flex>
}

export default LiveViewHeader;