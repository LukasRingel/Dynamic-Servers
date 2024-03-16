import * as React from "react";
import HomeComponent from "../dashboard/HomeComponent";
import NotFoundPage from "../../components/404NotFound";
import ViewContainer from "../view/ViewContainer";
import SearchPage from "../search/SearchPage";

export class Route {
  path: string;
  page: React.ComponentType;

  constructor(path: string, page: React.ComponentType) {
    this.path = path;
    this.page = page;
  }
}

export const routes = [
  new Route("/view/:mode/:serverId", ViewContainer),
  new Route("/search", SearchPage),
  new Route("/", HomeComponent),
  new Route("*", NotFoundPage)
];

