package it.uniroma3.siw.spring.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
import it.uniroma3.siw.spring.controller.validator.UtenteValidator;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CampoService;
import it.uniroma3.siw.spring.service.PrenotazioneService;

@Controller
public class PrenotazioneController {
	
	private static final Logger logger = LogManager.getLogger(PrenotazioneController.class);
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private CampoService campoService;
	
	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	@Autowired
<<<<<<< HEAD
	private UtenteValidator utenteValidator;
=======
	private EmailService emailService;
	
	//TODO: Get all'add prenotazione dove creo la prenotazione
>>>>>>> 7badfbde597ca0d23bd1115245341ac9052c6111
	
	@RequestMapping(value="/addPrenotazione", method = RequestMethod.POST)
	public String addPrenotazione(@ModelAttribute("utente") Utente utente, 
								  @ModelAttribute("campo_id") Long campo_id,
								  @ModelAttribute("hinizio") String hinizio,
								  @ModelAttribute("hfine") String hfine,
								  @ModelAttribute("prenotazione") Prenotazione prenotazione,
										Model model, BindingResult bindingResult) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		prenotazione.setOrarioInizio(LocalTime.parse(hinizio, formatter));
		prenotazione.setOrarioFine(LocalTime.parse(hfine, formatter));
		
		prenotazioneValidator.validate(prenotazione, bindingResult);
		utenteValidator.validate(utente, bindingResult);
		if(!bindingResult.hasErrors()) {
<<<<<<< HEAD
=======
			prenotazione.setUtente(utente);
			prenotazioneService.inserisci(prenotazione);
			String email = utente.getEmail().trim();
			String codice = prenotazione.getCodice();
			emailService.sendSimpleMessage(email, "Conferma prenotazione", 
					"Codice per confermare la prenotazione: http://localhost:8090/confermaPrenotazione/" + codice);
			return "campi.html";
		}
			logger.debug(campo_id);
>>>>>>> 7badfbde597ca0d23bd1115245341ac9052c6111
			if(!prenotazioneService.alreadyExists((Long) campo_id, prenotazione)) {
				prenotazione.setUtente(utente);
				campoService.campoPerId(campo_id).aggiungiPrenotazione(prenotazione);
				prenotazioneService.inserisci(prenotazione);
				return "campi.html";
			}
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