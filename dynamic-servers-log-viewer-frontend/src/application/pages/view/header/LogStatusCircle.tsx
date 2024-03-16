import React from "react";

type ItemListProps = {
  running: boolean
};

const LogStatusCircle: React.FC<ItemListProps> = ({running}) => {

  const circleStyles = {
    width: "15px",
    height: "15px",
    borderRadius: "50%",
    marginRight: "5px",
    backgroundColor: running ? "#299F00" : "#9F0000"
  }

  return <div style={circleStyles}/>
}

export default LogStatusCircle;