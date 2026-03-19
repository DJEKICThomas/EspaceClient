import i18n from 'i18next';
import { initReactI18next } from "react-i18next";
import LanguageDetector from 'i18next-browser-languagedetector';

i18n.use(initReactI18next).use(LanguageDetector).init({
    fallbackLng: "en",
    interpolation: {
        escapeValue: false
    },
    resources: {
        fr: {
            translation: {
                "home": "Accueil",
                "logout": "Déconnexion",
                "invoices": "Factures",
                "orders": "Commandes",
                "offers": "Offres",
                "historic": "Historique",
                "admin": "Admin",
                "documents": "Documents",
                "subscriptions": "Abonnements",

                //Dans home
                "my_client" : "Mes clients",
                "dashboard" : "Tableau de bord",
                "welcome_home" : "Bienvenue dans votre espace client ",
                "active_clients" : "Clients actifs",
                "total": "Total",

                //Dans offres
                "my_offers": "Mes offres",
                "offers_label": "offre(s)",
                "awaiting_your_approval" : "En attente de votre validation",
                "no_offers": "Pas d'offres",
                "consult_rep_for_quote": "Consultez votre délégué pour un devis",
                "last_proposal": "Dernière proposition",
                "received_on": "Reçue le",
                "reference": "Référence",
                "offer_details": "Détails de l'offre",
                "article": "Article",
                "designation": "Désignation",
                "unit_price": "Prix unitaire",
                "quantity": "Quantité",
                "col_offer_num": "N° Offre",
                "date": "Date",
                "label": "Libellé",
                "customer_ref": "Référence Client",
                "supplier": "Fournisseur",
                "amount": "Montant",
                "expiryDate": "Expire le",

                "search": "Rechercher",

                //Dans historique
                "order_details": "Détails de la commande",
                "orders_count": "commande(s)",
                "no_history": "Aucun historique",
                "history_followup": "Suivi de votre historique",
                "history_empty": "Historique vide",
                "history_value": "Valeur de l'historique",
                "history_total_sum": "Somme totale de votre historique",
                "last_order": "Dernière commande",
                "placed_on": "Passée le",
                "col_order_num": "N° Commande",
                "col_date_creation": "Date de création",
                "col_date_closure": "Date de clôture",

                //Dans commandes
                "my_orders": "Mes commandes",
                "no_orders": "Aucune commande",
                "track_current_orders": "Suivi de vos commandes en cours",
                "place_first_order": "Passez votre première commande dès maintenant",

                //Dans factures
                "last_invoice": "Dernière facture",
                "issued_on": "Émise le",
                "due_date": "Échéance",
                "total_balance_due": "Solde total dû",
                "invoices_pending": "Factures en attente de paiement",
                "up_to_date": "Vous êtes à jour",
                "col_inv_num": "N° Facture",
                "invoice_details": "Détails de la facture",
                "debit_due": "Débit (Dû)",
                "status": "Statut",
                "paid": "Payée",
                "to_settle": "À régler",
                "col_inv_action": "Actions",

                //Dans abonnement
                "start_date": "Date de début",
                "end_date": "Date de fin",
                "my_subscriptions": "Mes abonnements",
                "subscriptions_count": "Nombre d'abonnements",
                "no_subscriptions": "Aucun abonnement",
                "track_active_services": "Suivre les services actifs",
                "subscription_status": "État de l'abonnement",
                "active_contracts": "Abonnements actifs",
                "active_now": "Actif maintenant",
                "expired": "Expiré",
                "subscription_details" : "Détails de l'abonnement",
                "frequency" : "Périodicité",
                "next_due_date" : "Prochaine échéance",

                //Dans admin
                "actions": "Actions",
                "edit": "Modifier",
                "permissions": "Permissions",
                "active": "Actif",
                "inactive": "Inactif",
                "email": "Email",
                "lastname": "Nom",
                "firstname": "Prénom",
                "function": "Fonction",
                "profile": "Profil",
                "confirm_delete": "Confirmer la suppression ?",
                "delete": "Supprimer",
                "user_deleted": "Utilisateur supprimé",
                "yes": "Oui",
                "no": "Non",

                //Permissions user
                "account": "Compte",
                "permissions_access": "Permissions & Accès",
                "back_button": "RETOUR",
                "col_code": "Code",
                "col_description": "Description",
                "col_access": "Accès",
                "permission_added_success": "Permission modifiée",
                "permission_removed_success": "Permission enlevée",
                "permission_updated_error": "Erreur pendant la modification",

                //Profils user
                "user_account": "Compte utilisateur",
                "password": "Mot de passe",
                "confirm_password": "Confirmation de mot de passe",
                "title_label": "Titre",
                "phone": "Téléphone",
                "mobile": "GSM",
                "fax": "Fax",
                "login_validity": "Date de validité du login",
                "function_label": "Fonction dans l'entreprise",
                "active_account": "Compte actif",
                "confirm_button": "CONFIRMER",

                //Dans login
                "customer_area": "Espace client",
                "username": "Nom d'utilisateur",
                "login_button": "Connexion",
                "register_button": "S'inscrire",
                "forgot_password": "Mot de passe oublié ?",
                "error_incorrect_id": "Identifiant incorrect",
                "error_invalid_credentials": "Nom d'utilisateur ou mot de passe incorrect.",
                "error_server_unavailable": "Le serveur est indisponible",

                "grid_group_panel": "Faites glisser une colonne ici pour grouper",

                //Dans charts dashboard
                "chart_monthly_revenue": "Chiffre d'affaires mensuel",
                "chart_revenue": "Chiffre d'affaires",
                "chart_subscription_expiry": "Analyse des échéances",
                "chart_paid_vs_outstanding": "Payé vs En attente",
                "chart_outstanding": "En attente",
                "expiring_soon": "Expire bientôt",
                "valid": "Valide",

                //Préférences
                "dark_mode": "Mode sombre",
                "light_mode": "Mode clair",
            }
        },
        en: {
            translation: {
                "home": "Home",
                "logout": "Logout",
                "invoices": "Invoices",
                "orders": "Orders",
                "offers": "Offers",
                "historic": "History",
                "admin": "Admin",
                "documents": "Documents",
                "subscriptions": "Subscriptions",

                "search": "Search",

                //Dans home
                "my_client" : "My clients",
                "dashboard" : "Dashboard",
                "welcome_home" : "Welcome to your dashboard",
                "active_clients" : "Active customers",
                "total": "Total",

                //Dans offres
                "my_offers": "My Offers",
                "offers_label": "offer(s)",
                "no_offers": "No offers",
                "awaiting_your_approval": "Awaiting your approval",
                "consult_rep_for_quote": "Contact your representative for a quote",
                "last_proposal": "Last proposal",
                "received_on": "Received on",
                "reference": "Reference",
                "offer_details": "Offer details",
                "article": "Item",
                "designation": "Description",
                "unit_price": "Unit Price",
                "quantity": "Quantity",
                "col_offer_num": "Offer No.",
                "date": "Date",
                "label": "Label",
                "customer_ref": "Customer Ref",
                "supplier": "Supplier",
                "amount": "Amount",
                "expiryDate": "Expires on",

                //Dans historique
                "order_details": "Order Details",
                "orders_count": "order(s)",
                "no_history": "No history",
                "history_followup": "Track your history",
                "history_empty": "History empty",
                "history_value": "History Value",
                "history_total_sum": "Total amount of your history",
                "last_order": "Last Order",
                "placed_on": "Placed on",
                "col_order_num": "Order No.",
                "col_date_creation": "Creation Date",
                "col_date_closure": "Closure Date",

                //Dans commandes
                "my_orders": "My orders",
                "no_orders": "No orders",
                "track_current_orders": "Track current orders",
                "place_first_order": "Place your first order",

                //Dans factures
                "last_invoice": "Latest invoice",
                "issued_on": "Issued on",
                "due_date": "Due date",
                "total_balance_due": "Total balance due",
                "invoices_pending": "Invoices pending payment",
                "up_to_date": "You are up to date",
                "col_inv_num": "Invoice No.",
                "invoice_details": "Invoice details",
                "debit_due": "Debit (Due)",
                "status": "Status",
                "paid": "Paid",
                "to_settle": "To settle",
                "col_inv_action": "Actions",

                //Dans abonnement
                "start_date": "Start date",
                "end_date": "End date",
                "my_subscriptions": "My subscriptions",
                "subscriptions_count": "Subscriptions count",
                "no_subscriptions": "No subscriptions",
                "track_active_services": "Track active services",
                "subscription_status": "Subscription status",
                "active_contracts": "Active contracts",
                "active_now": "Active now",
                "expired": "Expired",
                "frequency": "Frequency",
                "subscription_details" : "Subscription details",
                "next_due_date" : "Next due date",

                //Admin
                "actions": "Actions",
                "edit": "Edit",
                "permissions": "Permissions",
                "active": "Active",
                "inactive": "Inactive",
                "email": "Email",
                "lastname": "Last Name",
                "firstname": "First Name",
                "function": "Function",
                "profile": "Profile",
                "confirm_delete": "Do you confirm the delete ?",
                "delete": "Delete",
                "user_deleted": "User deleted",
                "yes": "Yes",
                "no": "No",

                //Permissions user
                "account": "Account",
                "permissions_access": "Permissions & Access",
                "back_button": "BACK",
                "col_code": "Code",
                "col_description": "Description",
                "col_access": "Access",
                "permission_added_success": "Permission added",
                "permission_removed_success": "Permission removed",
                "permission_updated_error": "Error during modification",

                //Profils user
                "user_account": "User account",
                "password": "Password",
                "confirm_password": "Confirm password",
                "title_label": "Title",
                "phone": "Phone",
                "mobile": "Mobile",
                "fax": "Fax",
                "login_validity": "Login validity date",
                "function_label": "Function in the company",
                "active_account": "Active account",
                "confirm_button": "CONFIRM",

                //Login
                "customer_area": "Customer Area",
                "username": "Username",
                "login_button": "Login",
                "register_button": "Register",
                "forgot_password": "Forgot password?",
                "error_incorrect_id": "Incorrect ID",
                "error_invalid_credentials": "Username or password incorrect.",
                "error_server_unavailable": "Server is unavailable",

                "grid_group_panel": "Drag a column header here to group",

                //Dashboard charts
                "chart_monthly_revenue": "Monthly Revenue",
                "chart_revenue": "Revenue",
                "chart_subscription_expiry": "Subscription Expiry Analysis",
                "chart_paid_vs_outstanding": "Paid vs Outstanding",
                "chart_outstanding": "Outstanding",
                "expiring_soon": "Expiring Soon",
                "valid": "Valid",

                //Preferences
                "dark_mode": "Dark Mode",
                "light_mode": "Light Mode",
            }
        },
        de: {
            translation: {
                "home": "Startseite",
                "logout": "Abmelden",
                "invoices": "Rechnungen",
                "orders": "Bestellungen",
                "offers": "Angebote",
                "historic": "Verlauf",
                "admin": "Admin",
                "documents": "Dokumente",
                "subscriptions": "Abonnements",

                "search": "Suchen",

                //Dans home
                "my_client" : "Meine Kunden",
                "dashboard" : "Armaturenbrett",
                "welcome_home" : "Willkommen in Ihrem Dashboard",
                "active_clients" : "Aktive Kunden",
                "total": "Gesamt",

                //Dans offres
                "my_offers": "Meine Angebote",
                "offers_label": "Angebot(e)",
                "no_offers": "Keine Angebote",
                "awaiting_your_approval": "Warten auf Ihre Genehmigung",
                "consult_rep_for_quote": "Kontaktieren Sie Ihren Vertreter für ein Angebot",
                "last_proposal": "Letzter Vorschlag",
                "received_on": "Erhalten am",
                "reference": "Referenz",
                "offer_details": "Details zum Angebot",
                "article": "Artikel",
                "designation": "Bezeichnung",
                "unit_price": "Einzelpreis",
                "quantity": "Menge",
                "col_offer_num": "Angebot Nr.",
                "date": "Datum",
                "label": "Bezeichnung",
                "customer_ref": "Kundenreferenz",
                "supplier": "Lieferant",
                "amount": "Betrag",
                "expiryDate": "Läuft ab am",

                //Dans historique
                "order_details": "Bestelldetails",
                "orders_count": "Bestellung(en)",
                "no_history": "Kein Verlauf",
                "history_followup": "Verfolgen Sie Ihren Verlauf",
                "history_empty": "Verlauf leer",
                "history_value": "Gesamtsumme",
                "history_total_sum": "Gesamtsumme Ihres Verlaufes",
                "last_order": "Letzte Bestellung",
                "placed_on": "Aufgegeben am",
                "col_order_num": "Bestell-Nr.",
                "col_date_creation": "Erstellungsdatum",
                "col_date_closure": "Abschlussdatum",

                //Dans commandes
                "my_orders": "Meine Bestellungen",
                "no_orders": "Keine Bestellungen",
                "track_current_orders": "Verfolgen Sie Ihre aktuellen Bestellungen",
                "place_first_order": "Geben Sie jetzt Ihre erste Bestellung auf",

                //Dans abonnement
                "start_date": "Startdatum",
                "end_date": "Enddatum",
                "my_subscriptions": "Meine Abonnements",
                "subscriptions_count": "Anzahl der Abonnements",
                "no_subscriptions": "Keine Abonnements",
                "track_active_services": "Aktive Dienste verfolgen",
                "subscription_status": "Abonnementstatus",
                "active_contracts": "Aktive Verträge",
                "active_now": "Jetzt aktiv",
                "expired": "Abgelaufen",
                "frequency": "Turnus",
                "subscription_details": "Abonnementdetails",
                "next_due_date" : "Nächster Termin",

                //Dans factures
                "last_invoice": "Letzte Rechnung",
                "issued_on": "Ausgestellt am",
                "due_date": "Fälligkeit",
                "total_balance_due": "Gesamtsaldo fällig",
                "invoices_pending": "Rechnungen stehen zur Zahlung an",
                "up_to_date": "Sie sind auf dem Laufenden",
                "invoice_details": "Rechnungsdetails",
                "col_inv_num": "Rechnungs-Nr.",
                "debit_due": "Soll (Fällig)",
                "status": "Status",
                "paid": "Bezahlt",
                "to_settle": "Zu begleichen",
                "col_inv_action": "Aktionen",

                //Admin
                "actions": "Aktionen",
                "edit": "Bearbeiten",
                "permissions": "Berechtigungen",
                "active": "Aktiv",
                "inactive": "Inaktiv",
                "email": "E-Mail",
                "lastname": "Nachname",
                "firstname": "Vorname",
                "function": "Funktion",
                "profile": "Profil",
                "confirm_delete": "Bestätigen Sie die Löschung?",
                "delete": "Löschen",
                "user_deleted": "Benutzer gelöscht",
                "yes": "Ja",
                "no": "Nein",

                //Permissions user
                "account": "Konto",
                "permissions_access": "Berechtigungen & Zugriff",
                "back_button": "ZURÜCK",
                "col_code": "Code",
                "col_description": "Beschreibung",
                "col_access": "Zugriff",
                "permission_added_success": "Berechtigung hinzugefügt",
                "permission_removed_success": "Berechtigung entfernt",
                "permission_updated_error": "Fehler beim Ändern",

                //Profils user
                "user_account": "Benutzerkonto",
                "password": "Passwort",
                "confirm_password": "Passwort bestätigen",
                "title_label": "Titel",
                "phone": "Telefon",
                "mobile": "Handy",
                "fax": "Fax",
                "login_validity": "Gültigkeitsdatum des Logins",
                "function_label": "Funktion im Unternehmen",
                "active_account": "Aktives Konto",
                "confirm_button": "BESTÄTIGEN",

                //Login
                "customer_area": "Kundenbereich",
                "username": "Benutzername",
                "login_button": "Anmelden",
                "register_button": "Registrieren",
                "forgot_password": "Passwort vergessen?",
                "error_incorrect_id": "Ungültige ID",
                "error_invalid_credentials": "Benutzername oder Passwort falsch.",
                "error_server_unavailable": "Server ist nicht erreichbar",

                "grid_group_panel": " Ziehen Sie eine Spaltenüberschrift hierher, um zu gruppieren",

                //Dashboard-Diagramme
                "chart_monthly_revenue": "Monatlicher Umsatz",
                "chart_revenue": "Umsatz",
                "chart_subscription_expiry": "Ablaufanalyse der Abonnements",
                "chart_paid_vs_outstanding": "Bezahlt vs Ausstehend",
                "chart_outstanding": "Ausstehend",
                "expiring_soon": "Läuft bald ab",
                "valid": "Gültig",

                //Einstellungen
                "dark_mode": "Dunkelmodus",
                "light_mode": "Hellmodus",
            }
        },
        nl: {
            translation: {
                "home": "Ontvangst",
                "logout": "Ontkoppeling",
                "invoices": "Facturen",
                "orders": "Bestellingen",
                "offers": "Aanbiedingen",
                "historic": "Geschiedenis",
                "admin": "Beheerder",
                "documents": "Documenten",
                "subscriptions": "Abonnementen",

                "search": "Zoeken",

                //Dans home
                "my_client" : "Mijn klanten",
                "dashboard" : "Dashboard",
                "welcome_home" : "Welkom bij uw dashboard",
                "active_clients" : "Actieve klanten",
                "total": "Totaal",


                //Dans offres
                "my_offers": "Mijn Offertes",
                "offers_label": "offerte(s)",
                "no_offers": "Geen offertes",
                "awaiting_your_approval": "Wachtend op uw goedkeuring",
                "consult_rep_for_quote": "Neem contact op met uw vertegenwoordiger voor een offerte",
                "last_proposal": "Laatste voorstel",
                "received_on": "Ontvangen op",
                "reference": "Referentie",
                "offer_details": "Details van het aanbod",
                "article": "Artikel",
                "designation": "Omschrijving",
                "unit_price": "Eenheidsprijs",
                "quantity": "Aantal",
                "col_offer_num": "Offertenr.",
                "date": "Datum",
                "label": "Beschrijving",
                "customer_ref": "Klantreferentie",
                "supplier": "Leverancier",
                "amount": "Bedrag",
                "expiryDate": "Verloopt op",

                //Dans historique
                "order_details": "Bestelgegevens",
                "orders_count": "bestelling(en)",
                "no_history": "Geen geschiedenis",
                "history_followup": "Volg uw geschiedenis",
                "history_empty": "Geschiedenis leeg",
                "history_value": "Waarde van de geschiedenis",
                "history_total_sum": "Totale som van uw geschiedenis",
                "last_order": "Laatste bestelling",
                "placed_on": "Geplaatst op",
                "col_order_num": "Bestelnr.",
                "col_date_creation": "Aanmaakdatum",
                "col_date_closure": "Sluitingsdatum",

                //Dans commandes
                "my_orders": "Mijn Bestellingen",
                "no_orders": "Geen bestellingen",
                "track_current_orders": "Volg uw lopende bestellingen",
                "place_first_order": "Plaats nu uw eerste bestelling",

                //Dans factures
                "last_invoice": "Laatste factuur",
                "issued_on": "Uitgegeven op",
                "due_date": "Vervaldatum",
                "total_balance_due": "Totaal verschuldigd saldo",
                "invoices_pending": "Facturen in afwachting van betaling",
                "up_to_date": "U bent helemaal bij",
                "col_inv_num": "Factuurnr.",
                "debit_due": "Debet (Verschuldigd)",
                "invoice_details": "Factuurgegevens",
                "status": "Status",
                "paid": "Betaald",
                "to_settle": "Te betalen",
                "col_inv_action": "Acties",

                //Dans abonnement
                "start_date": "Startdatum",
                "end_date": "Einddatum",
                "my_subscriptions": "Mijn abonnementen",
                "subscriptions_count": "Aantal abonnementen",
                "no_subscriptions": "Geen abonnementen",
                "track_active_services": "Actieve diensten volgen",
                "subscription_status": "Abonnementsstatus",
                "active_contracts": "Actieve contracten",
                "active_now": "Nu actief",
                "expired": "Verlopen",
                "frequency": "Periodiciteit",
                "subscription_details": "Abonnementsgegevens",
                "next_due_date" : "Volgende vervaldatum",


                //Admin
                "actions": "Acties",
                "edit": "Bewerken",
                "permissions": "Rechten",
                "active": "Actief",
                "inactive": "Inactief",
                "email": "E-mail",
                "lastname": "Achternaam",
                "firstname": "Voornaam",
                "function": "Functie",
                "profile": "Profiel",
                "confirm_delete": "Bevestigt u de verwijdering?",
                "delete": "Verwijderen",
                "user_deleted": "Gebruiker verwijderd",
                "yes": "Ja",
                "no": "Nee",

                //Permissions user
                "account": "Account",
                "permissions_access": "Rechten & Toegang",
                "back_button": "TERUG",
                "col_code": "Code",
                "col_description": "Omschrijving",
                "col_access": "Toegang",
                "permission_added_success": "Toestemming toegevoegd",
                "permission_removed_success": "Toestemming verwijderd",
                "permission_updated_error": "Fout tijdens het wijzigen",

                //Profils user
                "user_account": "Gebruikersaccount",
                "password": "Wachtwoord",
                "confirm_password": "Wachtwoord bevestigen",
                "title_label": "Titel",
                "phone": "Telefoon",
                "mobile": "GSM",
                "fax": "Fax",
                "login_validity": "Geldigheidsdatum login",
                "function_label": "Functie in het bedrijf",
                "active_account": "Actief account",
                "confirm_button": "BEVESTIGEN",

                //Login
                "customer_area": "Klantenzone",
                "username": "Gebruikersnaam",
                "login_button": "Inloggen",
                "register_button": "Registreren",
                "forgot_password": "Wachtwoord vergeten?",
                "error_incorrect_id": "Onjuiste ID",
                "error_invalid_credentials": "Gebruikersnaam of wachtwoord onjuist.",
                "error_server_unavailable": "Server is niet beschikbaar",

                "grid_group_panel": "  Sleep een kolomkop hierheen om te groeperen",

                //Dashboard grafieken
                "chart_monthly_revenue": "Maandelijkse omzet",
                "chart_revenue": "Omzet",
                "chart_subscription_expiry": "Vervalanalyse abonnementen",
                "chart_paid_vs_outstanding": "Betaald vs Openstaand",
                "chart_outstanding": "Openstaand",
                "expiring_soon": "Verloopt binnenkort",
                "valid": "Geldig",

                //Voorkeuren
                "dark_mode": "Donkere modus",
                "light_mode": "Lichte modus",
            }
        }
    }
});

export default i18n;