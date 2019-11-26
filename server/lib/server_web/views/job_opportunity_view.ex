defmodule ServerWeb.JobOpportunityView do
  use ServerWeb, :view
  alias ServerWeb.JobOpportunityView

  def render("index.json", %{opportunities: opportunities}) do
    %{data: render_many(opportunities, JobOpportunityView, "job_opportunity.json")}
  end

  def render("show.json", %{job_opportunity: job_opportunity}) do
    %{data: render_one(job_opportunity, JobOpportunityView, "job_opportunity.json")}
  end

  def render("job_opportunity.json", %{job_opportunity: job_opportunity}) do
    %{id: job_opportunity.id,
      nomeEmpresa: job_opportunity.nomeEmpresa,
      contato: job_opportunity.contato,
      area: job_opportunity.area,
      tempo: job_opportunity.tempo,
      salario: job_opportunity.salario}
  end
end
