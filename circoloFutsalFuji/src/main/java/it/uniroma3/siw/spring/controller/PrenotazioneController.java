package it.uniroma3.siw.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.EmailService;
import it.uniroma3.siw.spring.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.spring.model.Campo;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.PrenotazioneService;

@Controller
public class PrenotazioneController {
	
	private static Logger logger = LogManager.getLogger(PrenotazioneController.class);
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	@Autowired
	private EmailService emailService;
	
	//TODO: Get all'add prenotazione dove creo la prenotazione
	
	@RequestMapping(value="/addPrenotazione", method = RequestMethod.POST)
	public String addPrenotazione(@ModelAttribute("utente") Utente utente, 
								  @ModelAttribute("campo") Campo campo,
								  @ModelAttribute("prenotazione") Prenotazione prenotazione,
								  Model model, BindingResult bindingResult) {
		prenotazioneValidator.validate(prenotazione, bindingResult);
		if(!bindingResult.hasErrors()) {
			prenotazione.setUtente(utente);
			prenotazioneService.inserisci(prenotazione);
			String email = utente.getEmail().trim();
			String codice = prenotazione.getCodice();
			emailService.sendSimpleMessage(email, "Conferma prenotazione", 
					"Codice per confermare la prenotazione: http://localhost:8090/confermaPrenotazione/" + codice);
			return "campi.html";
		}
		return "prenotazione.html";
	}
	
	@RequestMapping(value = "/confermaPrenotazione/{codice}", method = RequestMethod.GET)
	public String confermaPrenotazione(Model model, @PathVariable("codice") String codiceConferma) {
		Prenotazione prenotazione = this.prenotazioneService.prenotazionePerCodice(codiceConferma);
		prenotazione.setConfermata(true);
		//TODO: Update della prenotazione nel DB, ora che e' confermata
		logger.debug("la prenotazione in memoria e' confermata\n Quella nel db? e' confermata: " + this.prenotazioneService.prenotazionePerCodice(codiceConferma).isConfermata());
		return "index.html"; 
	}
}
