import {Stop} from "./Stop";

export interface Leg {
  id: number,
  legTime: String
  originStop: Stop
  destinationStop: Stop
}
