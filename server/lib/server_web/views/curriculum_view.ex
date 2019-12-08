defmodule ServerWeb.CurriculumView do
  use ServerWeb, :view
  alias ServerWeb.CurriculumView

  def render("index.json", %{curriculuns: curriculuns}) do
    %{data: render_many(curriculuns, CurriculumView, "curriculum.json")}
  end

  def render("show.json", %{curriculum: curriculum}) do
    %{data: render_one(curriculum, CurriculumView, "curriculum.json")}
  end

  def render("curriculum.json", %{curriculum: curriculum}) do
    %{id: curriculum.id,
      nome: curriculum.nome,
      contato: curriculum.contato,
      area: curriculum.area,
      tempo: curriculum.tempo,
      salario: curriculum.salario}
  end
end
