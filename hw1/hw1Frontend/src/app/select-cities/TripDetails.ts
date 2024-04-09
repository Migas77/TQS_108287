import {Route} from "@angular/router";

export interface TripDetails {
  id: number,
  route: Route
  availableSeats: number[]
  numberOfSeats: number
  price: number
  currency: String
  departureTime: Date
}
