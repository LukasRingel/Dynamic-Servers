import React from "react";
import {Flex} from "antd";
import OpenLogContainer from "../pages/navigation/open/OpenLogContainer";
import {ColorMode} from "../color/ColorMode";

type ItemListProps = {
  colorMode: ColorMode
};

const ApplicationHeader: React.FC<ItemListProps> = ({colorMode}) => {
  return <Flex justify={"flex-start"} align={"center"}>
    <OpenLogContainer colorMode={colorMode}/>
  </Flex>
}

export default ApplicationHeader;