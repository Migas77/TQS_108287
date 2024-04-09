import {RouteTrip} from "./RouteTrip";

export interface ReservationTrip {
  id: number,
  route: RouteTrip
  numberOfSeats: number
  priceEuros: number
  departureTime: String
}
