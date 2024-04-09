import { Injectable } from '@angular/core';
import {Stop} from "./select-cities/Stop";
import {TripDetails} from "./select-cities/TripDetails";

@Injectable({
  providedIn: 'root'
})
export class TripService {

  private baseURL : string = "http://localhost:8080/api/trips";

  constructor() { }

  async getTripsBetweenCities(originId: number, destinationId: number, currency: String, departure_date: String): Promise<TripDetails[]>{
    const url: string = this.baseURL + `?originId=${originId}&destinationId=${destinationId}&currency=${currency}&departure_date=${departure_date}`;
    const data: Response = await fetch(url);
    return await data.json() ?? [];
  }
}
