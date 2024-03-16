import React from "react";
import {Result} from "antd";

type ItemListProps = {};

const NotFoundPage: React.FC<ItemListProps> = (props) => {
  return <Result
    status="404"
    title="404"
    subTitle={"404"}
  />
}

export default NotFoundPage;