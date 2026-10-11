import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

/* Parse une date qui peut être un tableau [year, month, day] ou une string/Date. */
function parseDate(dateValue) {
    if (!dateValue) return null;
    if (Array.isArray(dateValue)) {
        return new Date(dateValue[0], dateValue[1] - 1, dateValue[2]);
    }
    const d = new Date(dateValue);
    return isNaN(d.getTime()) ? null : d;
}

/*
* Calcule le nombre de jours entre aujourd'hui et une date cible
* Positif = dans le futur, négatif = dans le passé
*/
function daysUntil(targetDate) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const target = new Date(targetDate);
    target.setHours(0, 0, 0, 0);
    return Math.ceil((target - today) / (1000 * 60 * 60 * 24));
}

/*
* Hook qui calcule les alertes à partir des données déjà chargées
* Retourne un tableau d'alertes triées par sévérité (urgent → attention)
* */
export default function useAlerts(invoices = [], subscriptions = []) {
    const { t, i18n } = useTranslation();
    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    return useMemo(() => {
        const alerts = [];
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        // --- Factures impayées ---
        invoices.forEach(inv => {
            if (inv.paid) return;

            const dueDate = parseDate(inv.dueDate);
            if (!dueDate) return;

            const days = daysUntil(dueDate);
            const amount = (inv.valCurD || 0).toLocaleString(locale, { style: 'currency', currency: inv.cur || 'EUR' });

            if (days < 0) {
                // Échéance dépassée
                alerts.push({
                    id: `inv-overdue-${inv.num}`,
                    severity: 'urgent',
                    sortOrder: 0,
                    icon: '🔴',
                    title: t('alert_invoice_overdue', { num: inv.num, amount }),
                    subtitle: t('alert_overdue_since', { days: Math.abs(days) }),
                    action: 'invoices',
                    filterField: 'num',
                    filterValue: inv.num,
                });
            } else if (days <= 7) {
                // Échéance dans 7 jours ou moins
                alerts.push({
                    id: `inv-soon-${inv.num}`,
                    severity: 'urgent',
                    sortOrder: 1,
                    icon: '🔴',
                    title: t('alert_invoice_due_soon', { num: inv.num, amount }),
                    subtitle: t('alert_due_in_days', { days }),
                    action: 'invoices',
                    filterField: 'num',
                    filterValue: inv.num,
                });
            } else if (days <= 30) {
                // Échéance dans 30 jours
                alerts.push({
                    id: `inv-warning-${inv.num}`,
                    severity: 'warning',
                    sortOrder: 2,
                    icon: '🟠',
                    title: t('alert_invoice_upcoming', { num: inv.num, amount }),
                    subtitle: t('alert_due_in_days', { days }),
                    action: 'invoices',
                    filterField: 'num',
                    filterValue: inv.num,
                });
            }
        });

        // --- Abonnements ---
        subscriptions.forEach(sub => {
            const endDate = parseDate(sub.endDate);
            if (!endDate) return;

            const days = daysUntil(endDate);
            const label = sub.description || sub.reference || sub.docCtr || '';

            if (days < 0) {
                // Expiré
                alerts.push({
                    id: `sub-expired-${sub.id || sub.docCtr}`,
                    severity: 'urgent',
                    sortOrder: 0,
                    icon: '🔴',
                    title: t('alert_subscription_expired', { name: label }),
                    subtitle: t('alert_expired_since', { days: Math.abs(days) }),
                    action: 'subscriptions',
                    filterField: 'reference',
                    filterValue: sub.reference,
                });
            } else if (days <= 30) {
                // Expire bientôt
                alerts.push({
                    id: `sub-expiring-${sub.id || sub.docCtr}`,
                    severity: 'warning',
                    sortOrder: 2,
                    icon: '🟠',
                    title: t('alert_subscription_expiring', { name: label }),
                    subtitle: t('alert_expires_in_days', { days }),
                    action: 'subscriptions',
                    filterField: 'reference',
                    filterValue: sub.reference,
                });
            }
        });

        // Tri : urgent d'abord, puis warning
        alerts.sort((a, b) => a.sortOrder - b.sortOrder);

        return alerts;
    }, [invoices, subscriptions, t, locale]);
}
