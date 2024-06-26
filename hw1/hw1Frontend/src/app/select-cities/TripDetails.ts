import {RouteTrip} from "./RouteTrip";

export interface TripDetails {
  id: number,
  route: RouteTrip
  availableSeats: number[]
  numberOfSeats: number
  price: number
  currency: String
  departureTime: String
}
