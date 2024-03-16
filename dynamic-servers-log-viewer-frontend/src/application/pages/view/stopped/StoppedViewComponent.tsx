import React, {CSSProperties} from "react";
import ViewLogComponent from "../ViewLogComponent";
import {mapByteArrayToStringArray} from "../ByteArrayToStringArrayMapper";

type ItemListProps = {
  lines: string
};

const StoppedViewComponent: React.FC<ItemListProps> = ({lines}) => {
  const scrollInContainerStyle: CSSProperties = {
    overflowY: 'scroll',
    height: 'calc(100vh - 130px)',
  }

  return (<div style={scrollInContainerStyle}>
    <ViewLogComponent lines={mapByteArrayToStringArray(lines)}/>
  </div>)
}

export default StoppedViewComponent;