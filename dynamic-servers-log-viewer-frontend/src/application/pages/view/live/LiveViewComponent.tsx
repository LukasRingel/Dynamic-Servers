import React, {CSSProperties, useEffect} from "react";
import {LiveViewData} from "./data/LiveViewData";
import ViewLogComponent from "../ViewLogComponent";

type ItemListProps = {
  sessionData: LiveViewData | null
};

const LiveViewComponent: React.FC<ItemListProps> = ({sessionData}) => {
  const [logLines, setLogLines] = React.useState<string[]>([]);

  useEffect(() => {
    if (sessionData !== null) {
      const ws = new WebSocket(sessionData.websocketUrl);
      ws.onopen = () => {
        ws.send(JSON.stringify({
          action: "subscribe",
          target: sessionData.session
        }))
      };
      ws.onmessage = (event) => {
        setLogLines(logLines => [...logLines, event.data])
        window.scrollTo(0, document.body.scrollHeight)
      };
      return () => {
        ws.close();
      }
    }
  }, [sessionData]);

  const scrollInContainerStyle: CSSProperties = {
    overflowY: 'scroll',
    height: 'calc(100vh - 130px)',
  }

  return <div style={scrollInContainerStyle}>
    <ViewLogComponent lines={logLines}/>
  </div>
}

export default LiveViewComponent;