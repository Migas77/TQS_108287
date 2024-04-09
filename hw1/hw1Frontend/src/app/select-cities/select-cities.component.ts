import {Component, inject} from '@angular/core';
import {Stop} from "./Stop";
import {StopService} from "../stop.service";
import {of} from "rxjs";
import {NgForOf, NgIf} from "@angular/common";
import {TripService} from "../trip.service";
import {TripDetails} from "./TripDetails";
import {FormsModule} from "@angular/forms";
import { NgbCalendar, NgbDatepickerModule, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { JsonPipe } from '@angular/common';
import {CurrencyService} from "../currency.service";
import {CurrencyDetails} from "./CurrencyDetails";
import {join} from "@angular/compiler-cli";
import {Leg} from "./Leg";
import {TripDetailsComponent} from "../trip-details/trip-details.component";

@Component({
  selector: 'app-select-cities',
  standalone: true,
  imports: [NgForOf, FormsModule, NgbDatepickerModule, JsonPipe, NgIf, TripDetailsComponent],
  templateUrl: './select-cities.component.html',
  styleUrl: './select-cities.component.css'
})
export class SelectCitiesComponent {
  stops : Stop[] = []
  trips: TripDetails[] = []
  currencies: String[] = []
  originStop: Stop | null = null;
  destinationStop: Stop | null = null;
  selectedCurrency: String | null = null;
  stopService: StopService = inject(StopService)
  tripService: TripService = inject(TripService)
  currencyService: CurrencyService = inject(CurrencyService)
  today = inject(NgbCalendar).getToday();
  departure_date: NgbDateStruct = this.today;
  tripToPass: TripDetails | null = null;

  constructor() {
    console.log("today", this.today)
    console.log("model", this.departure_date)
    this.stopService.getStops().then((stops: Stop[]) => {
      console.log("stops", stops)
      this.stops = stops;
    })
    this.currencyService.getAllCurrencies().then((currencies: CurrencyDetails[]) => {
      this.currencies = Object.keys(currencies);
      console.log("currencies", this.currencies)
    })
  }

  searchTrips(){
    console.log("originStop", this.originStop?.id)
    console.log("destinationStop", this.destinationStop)
    console.log("departureDate", this.departure_date, `${String(this.departure_date.year).padStart(4, "0")}-${String(this.departure_date.month).padStart(2, "0")}-${String(this.departure_date.day).padStart(2, "0")}`)
    console.log("selectedCurrency", this.selectedCurrency)
    if (this.originStop!=null && this.destinationStop!=null && this.departure_date!=null && this.selectedCurrency!=null){
      this.tripService.getTripsBetweenCities(this.originStop.id, this.destinationStop.id, this.selectedCurrency,
        `${String(this.departure_date.year).padStart(4, "0")}-${String(this.departure_date.month).padStart(2, "0")}-${String(this.departure_date.day).padStart(2, "0")}`)
        .then((trips: TripDetails[]) => {
          this.trips = trips;
          console.log(trips)
        })
    }
  }

  getTripString(trip: TripDetails){
    return trip.route.legs.map((leg: Leg) => leg.originStop.name).join(' -> ')
  }

  passTripToChild(trip: TripDetails){
    this.tripToPass=trip;
  }

  protected readonly of = of;
}
