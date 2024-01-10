CREATE OR REPLACE FUNCTION approvedUsers()
RETURNS INTEGER AS $$
DECLARE
    contador INTEGER;
BEGIN
    EXECUTE 'select count(*) from usuarios where estado_aprobacion=''APPROVED''' INTO contador;
    RETURN contador;
END;
$$ LANGUAGE plpgsql;