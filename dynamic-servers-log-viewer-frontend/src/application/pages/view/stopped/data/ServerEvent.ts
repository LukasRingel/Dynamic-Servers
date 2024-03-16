export class ServerEvent {
  type: string;
  timestamp: number;
  metaData: KeyValue;

  constructor(type: string, timestamp: number, metaData: KeyValue) {
    this.type = type;
    this.timestamp = timestamp;
    this.metaData = metaData;
  }
}

export class KeyValue {
  tag: string;
  source: string;

  constructor(tag: string, source: string) {
    this.tag = tag;
    this.source = source;
  }
}