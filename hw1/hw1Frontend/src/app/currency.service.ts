import { Injectable } from '@angular/core';
import {TripDetails} from "./select-cities/TripDetails";
import {CurrencyDetails} from "./select-cities/CurrencyDetails";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private baseURL : string = "https://api.frankfurter.app/currencies";

  constructor() { }

  async getAllCurrencies(): Promise<CurrencyDetails[]>{
    const url: string = this.baseURL;
    const data: Response = await fetch(url);
    return await data.json() ?? [];
  }
}
