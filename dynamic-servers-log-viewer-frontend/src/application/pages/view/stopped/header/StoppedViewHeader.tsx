import React, {useState} from "react";
import {Flex} from "antd";
import LogStatusCircle from "../../header/LogStatusCircle";
import {loadColorMode} from "../../../../color/ColorModeStorage";
import CopyLinkButton from "../../header/CopyLinkButton";
import ServerInfoButton from "./ServerInfoButton";
import ServerInfoDrawer from "./ServerInfoDrawer";

type ItemListProps = {
  serverId: string
};

const buttonDistance = {
  marginRight: "5px",
  marginLeft: "5px"
}

const StoppedViewHeader: React.FC<ItemListProps> = (props) => {
  const colorMode = loadColorMode()
  const [serverInfoVisible, setServerInfoVisible] = useState(false);

  const headerStyles = {
    backgroundColor: colorMode.headerBackground,
    padding: "5px",
    paddingLeft: "10px",
    height: "60px",
  }

  return <Flex vertical={false} justify={"space-between"} align={"center"} style={headerStyles}>
    <Flex align={"center"}>
      <LogStatusCircle running={false}/>
      {props.serverId}
    </Flex>
    <Flex align={"center"} style={buttonDistance}>
      <span style={buttonDistance}>
        <CopyLinkButton/>
      </span>
      <span>
        <ServerInfoButton toggle={() => setServerInfoVisible(!serverInfoVisible)}/>
        <ServerInfoDrawer
          serverInfoVisible={serverInfoVisible}
          close={() => setServerInfoVisible(false)}
          serverId={props.serverId}
        />
      </span>
    </Flex>
  </Flex>
}

export default StoppedViewHeader;