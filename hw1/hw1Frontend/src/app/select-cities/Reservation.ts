import {ReservationTrip} from "./ReservationTrip";

export interface Reservation {
  id: String,
  trip: ReservationTrip
  seatNumber: number
  clientName: String
  clientAddress: String
}
