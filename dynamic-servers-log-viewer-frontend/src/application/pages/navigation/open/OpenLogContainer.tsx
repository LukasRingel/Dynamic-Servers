import React from "react";
import {Input, notification} from "antd";
import {decideIfLiveOfStopped} from "./OpenLogDataSource";
import {useNavigate} from "react-router-dom";
import {ColorMode} from "../../../color/ColorMode";

type ItemListProps = {
  colorMode: ColorMode
};

const OpenLogContainer: React.FC<ItemListProps> = ({colorMode}) => {
  const navigate = useNavigate();
  const [input, setInput] = React.useState("");
  const [api, contextHolder] = notification.useNotification();

  function tryOpen() {
    if (input.length === 0) {
      return;
    }
    decideIfLiveOfStopped(input).then((response) => {
      navigate("/view/" + (response.live ? "live" : "stopped") + "/" + input);
    }).catch(() => {
      api.error({
        message: 'Unknown server id',
        description: 'The server id is not known. Please check the id and try again. Maybe the log has been deleted?',
        duration: 5,
      });
    })
  }

  const inputStyle = {
    height: 40,
    width: 400,
    backgroundColor: colorMode.background,
    border: 'none',
    color: colorMode.text
  }

  return <>
    {contextHolder}
    <Input
      key={"input"}
      style={inputStyle}
      placeholder={"Server-Id"}
      onInput={(event) => {
        setInput((event.target as HTMLInputElement).value)
      }}
      onPressEnter={tryOpen}
      autoFocus={true}
    />
  </>
}

export default OpenLogContainer;