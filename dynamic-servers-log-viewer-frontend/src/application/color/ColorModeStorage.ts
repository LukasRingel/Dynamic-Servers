import {ColorMode, darkMode, lightMode} from "./ColorMode";

export function storeColorMode(colorMode: ColorMode) {
  window.localStorage.setItem('colorMode', colorMode.name);
}

export function loadColorMode(): ColorMode {
  const mode = window.localStorage.getItem('colorMode');
  if (mode === 'dark') {
    return darkMode;
  }
  return lightMode;
}