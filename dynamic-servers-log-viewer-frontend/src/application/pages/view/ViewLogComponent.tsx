import React, {useEffect} from "react";

type ItemListProps = {
  lines: string[]
};

const textStyles = {
  fontSize: "0.8rem",
  marginLeft: "10px",
}

const scrollToBottom = (): boolean => {
  // scroll to element with id "target"
  const target = document.getElementById("target");
  if (target !== null) {
    target.scrollIntoView({behavior: "smooth"});
  }
  return true
};

const ViewLogComponent: React.FC<ItemListProps> = (props) => {
  useEffect(() => {
    scrollToBottom()
  });
  return <div style={{marginTop: 5}}>
    {
      props.lines.map((line, index) => {
        return <>
          <span style={textStyles} id={index === line.length ? "target": ""}>{line}</span>
          <br/>
        </>
      })
    }
  </div>
}

export default ViewLogComponent;