export function mapByteArrayToStringArray(byteArray: string) {
  return atob(byteArray).split('\n');
}