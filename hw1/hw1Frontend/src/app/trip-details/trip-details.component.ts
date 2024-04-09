import {Component, inject, Input} from '@angular/core';
import {TripDetails} from "../select-cities/TripDetails";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ReservationService} from "../reservation.service";
import {Reservation} from "../select-cities/Reservation";
import {Leg} from "../select-cities/Leg";
import {ReservationTrip} from "../select-cities/ReservationTrip";

@Component({
  selector: 'app-trip-details',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './trip-details.component.html',
  styleUrl: './trip-details.component.css'
})
export class TripDetailsComponent {
  @Input() trip : TripDetails | null = null;
  selectedSeatNumber: number | null = null;
  clientName: String | null = null;
  clientAddress: String | null = null;
  reservationService: ReservationService = inject(ReservationService)
  reservation: Reservation | null = null


  constructor() {
  }

  makeReservation() {
    console.log("selectedSeatNumber", this.selectedSeatNumber)
    console.log("clientName", this.clientName)
    console.log("clientAddress", this.clientAddress)
    if (this.trip!=null && this.selectedSeatNumber!=null && this.clientName!=null && this.clientAddress!=null){
      this.reservationService.makeReservation({
        tripId: this.trip.id,
        seatNumber: this.selectedSeatNumber,
        clientName: this.clientName,
        clientAddress: this.clientAddress
      }).then((reservation: Reservation) => {
        console.log(reservation)
        this.reservation = reservation
      })
    }
  }


  getTripString(trip: ReservationTrip){
    return trip.route.legs.map((leg: Leg) => leg.originStop.name).join(' -> ')
  }


}
