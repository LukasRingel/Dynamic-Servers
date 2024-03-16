import React from "react";
import {Result} from "antd";

type ItemListProps = {};

const ErrorPage: React.FC<ItemListProps> = (props) => {
  return <Result
    status="500"
    title="Server Error"
    subTitle={"Try again later or contact an administrator."}
  />
}

export default ErrorPage;