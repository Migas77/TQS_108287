import { Injectable } from '@angular/core';
import {ReservationDTO} from "./select-cities/ReservationDTO";
import {Reservation} from "./select-cities/Reservation";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private baseURL : string = "http://localhost:8080/api/reservations";

  constructor() { }

  async makeReservation(reservationDTO : ReservationDTO): Promise<Reservation>{
    const url: string = this.baseURL;
    const data: Response = await fetch(url, {
      method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(reservationDTO)
    });
    return data.json();
  }
}
